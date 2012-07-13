/**
 * Copyright (c) 2011, University of Konstanz, Distributed Systems Group
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the University of Konstanz nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sirix.io;

/**
 * Interface for providing byteAccess to the write-process in the storage. That
 * means that every serialization process in sirix is using this interface
 * and that the related concrete storage implementation is implementing this
 * interface.
 * 
 * @author Sebastian Graf, University of Konstanz
 * @author Johannes Lichtenberger, University of Konstanz
 * 
 */
public interface ITTSink {
  
  /**
   * Writing a long to the storage.
   * 
   * @param pLongVal
   *          to be written
   */
  void writeLong(long pLongVal);

  /**
   * Writing an int to the storage.
   * 
   * @param pIntVal
   *          to be written
   */
  void writeInt(int pIntVal);

  /**
   * Writing a byte to the storage.
   * 
   * @param pByteVal
   *          to be written
   */
  void writeByte(byte pByteVal);
  
  /**
   * Writing a byte-array to the storage.
   * 
   * @param pByteVals
   *          to be written
   */
  void writeBytes(byte[] pByteVals);
  
  /**
   * Writing a short to the storage.
   * 
   * @param pShortVal
   *          to be written
   */
  void writeShort(short pShortVal);
  
}
