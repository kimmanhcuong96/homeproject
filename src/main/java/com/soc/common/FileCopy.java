package com.soc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileCopy {
  public void copyFile(String sourcePath, String destinationPath) {
    InputStream inStream = null;
    OutputStream outStream = null;
    try {
      inStream = new FileInputStream(new File(sourcePath));
      outStream = new FileOutputStream(new File(destinationPath));

      int length;
      byte[] buffer = new byte[1024];

      // copy the file content in bytes
      while ((length = inStream.read(buffer)) > 0) {
          outStream.write(buffer, 0, length);
      }
      System.out.println("File is copied successful!");
  } catch (IOException e) {
      e.printStackTrace();
  }

  }
}
