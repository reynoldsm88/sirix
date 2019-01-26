/**
 * Copyright (c) 2011, University of Konstanz, Distributed Systems Group All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: * Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. * Redistributions
 * in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * * Neither the name of the University of Konstanz nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.sirix;

import java.nio.file.Files;
import java.nio.file.Path;
import org.sirix.XdmTestHelper.PATHS;
import org.sirix.access.Databases;
import org.sirix.access.conf.DatabaseConfiguration;
import org.sirix.access.conf.ResourceConfiguration;
import org.sirix.api.Database;
import org.sirix.api.ResourceManager;
import org.sirix.api.Transaction;
import org.sirix.api.xdm.XdmNodeReadOnlyTrx;
import org.sirix.api.xdm.XdmNodeTrx;
import org.sirix.api.xdm.XdmResourceManager;
import org.sirix.exception.SirixException;

/**
 * Generating a standard resource within the {@link PATHS#PATH1} path. It also generates a standard
 * resource defined within {@link XdmTestHelper#RESOURCE}.
 *
 * @author Sebastian Graf, University of Konstanz
 * @author Johannes Lichtenberger
 *
 */
public class Holder {

  /** {@link Database} implementation. */
  private Database<XdmResourceManager> mDatabase;

  /** {@link XdmResourceManager} implementation. */
  private XdmResourceManager mResMgr;

  /** {@link XdmNodeReadOnlyTrx} implementation. */
  private XdmNodeReadOnlyTrx mRtx;

  /** {@link XdmNodeTrx} implementation. */
  private XdmNodeTrx mWtx;

  private Transaction mTrx;

  /**
   * Generate a resource with deweyIDs for resources and open a resource.
   *
   * @return this holder instance
   * @throws SirixException if an error occurs
   */
  public static Holder generateDeweyIDResourceMgr() throws SirixException {
    final Path file = PATHS.PATH1.getFile();
    final DatabaseConfiguration config = new DatabaseConfiguration(file);
    if (!Files.exists(file)) {
      Databases.createXdmDatabase(config);
    }
    final var database = Databases.openXdmDatabase(PATHS.PATH1.getFile());
    database.createResource(
        new ResourceConfiguration.Builder(XdmTestHelper.RESOURCE, PATHS.PATH1.getConfig()).useDeweyIDs(true).build());
    final XdmResourceManager resourceManager = database.getResourceManager(XdmTestHelper.RESOURCE);
    final Holder holder = new Holder();
    holder.setDatabase(database);
    holder.setResourceManager(resourceManager);
    return holder;
  }

  /**
   * Generate a resource with a path summary.
   *
   * @return this holder instance
   * @throws SirixException if an error occurs
   */
  public static Holder generatePathSummary() throws SirixException {
    final Path file = PATHS.PATH1.getFile();
    final DatabaseConfiguration config = new DatabaseConfiguration(file);
    if (!Files.exists(file)) {
      Databases.createXdmDatabase(config);
    }
    final var database = Databases.openXdmDatabase(PATHS.PATH1.getFile());
    database.createResource(
        new ResourceConfiguration.Builder(XdmTestHelper.RESOURCE, PATHS.PATH1.getConfig()).buildPathSummary(true).build());
    final XdmResourceManager resourceManager = database.getResourceManager(XdmTestHelper.RESOURCE);
    final Holder holder = new Holder();
    holder.setDatabase(database);
    holder.setResourceManager(resourceManager);
    return holder;
  }

  /**
   * Open a resource manager.
   *
   * @return this holder instance
   * @throws SirixException if an error occurs
   */
  public static Holder openResourceManager() throws SirixException {
    final var database = XdmTestHelper.getDatabase(PATHS.PATH1.getFile());
    final XdmResourceManager resMgr = database.getResourceManager(XdmTestHelper.RESOURCE);
    final Holder holder = new Holder();
    holder.setDatabase(database);
    holder.setResourceManager(resMgr);
    return holder;
  }

  /**
   * Generate a {@link XdmNodeReaderWriter}.
   *
   * @return this holder instance
   * @throws SirixException if an error occurs
   */
  public static Holder generateWtx() throws SirixException {
    final Holder holder = openResourceManager();
    final XdmNodeTrx writer = holder.mResMgr.beginNodeTrx();
    holder.setXdmNodeWriteTrx(writer);
    return holder;
  }

  /**
   * Generate a {@link XdmNodeReadOnlyTrx}.
   *
   * @return this holder instance
   * @throws SirixException if an error occurs
   */
  public static Holder generateRtx() throws SirixException {
    final Holder holder = openResourceManager();
    final XdmNodeReadOnlyTrx reader = holder.mResMgr.beginReadOnlyTrx();
    holder.setXdmNodeReadTrx(reader);
    return holder;
  }

  /**
   * Close the database, session, read transaction and/or write transaction.
   *
   * @throws SirixException if an error occurs
   */
  public void close() throws SirixException {
    if (mRtx != null && !mRtx.isClosed()) {
      mRtx.close();
    }
    if (mWtx != null && !mWtx.isClosed()) {
      mWtx.rollback();
      mWtx.close();
    }
    if (mResMgr != null && !mResMgr.isClosed()) {
      mResMgr.close();
    }
    if (mTrx != null) {
      mTrx.close();
    }
    if (mDatabase != null) {
      mDatabase.close();
    }
  }

  /**
   * Get the {@link Database} handle.
   *
   * @return {@link Database} handle
   */
  public Database<XdmResourceManager> getDatabase() {
    return mDatabase;
  }

  /**
   * Get the {@link ResourceManager} handle.
   *
   * @return {@link ResourceManager} handle
   */
  public XdmResourceManager getResourceManager() {
    return mResMgr;
  }

  /**
   * Get the {@link XdmNodeReadOnlyTrx} handle.
   *
   * @return {@link XdmNodeReadOnlyTrx} handle
   */
  public XdmNodeReadOnlyTrx getNodeReadTrx() {
    return mRtx;
  }

  public Transaction getTrx() {
    return mTrx;
  }

  /**
   * Get the {@link XdmNodeTrx} handle.
   *
   * @return {@link XdmNodeTrx} handle
   */
  public XdmNodeTrx getXdmNodeWriteTrx() {
    return mWtx;
  }

  /**
   * Set the working {@link XdmNodeReaderWriter}.
   *
   * @param wtx {@link XdmNodeReaderWriter} instance
   */
  private void setXdmNodeWriteTrx(final XdmNodeTrx wtx) {
    mWtx = wtx;
  }

  /**
   * Set the working {@link XdmNodeReadOnlyTrx}.
   *
   * @param rtx {@link XdmNodeReadOnlyTrx} instance
   */
  private void setXdmNodeReadTrx(final XdmNodeReadOnlyTrx rtx) {
    mRtx = rtx;
  }

  /**
   * Set the working {@link ResourceManager}.
   *
   * @param pRtx {@link XdmNodeReadOnlyTrx} instance
   */
  private void setResourceManager(final XdmResourceManager resourceManager) {
    mResMgr = resourceManager;
  }

  /**
   * Set the working {@link Database}.
   *
   * @param pRtx {@link Database} instance
   */
  private void setDatabase(final Database<XdmResourceManager> database) {
    mDatabase = database;
  }

}
