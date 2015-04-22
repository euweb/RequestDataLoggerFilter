// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * this wrapper is needed for restoring the input stream after reading it
 * 
 * @author eugen
 *
 */
public class RequestDataWrapper extends HttpServletRequestWrapper {


  private final String requestData;


  public RequestDataWrapper( HttpServletRequest request ) throws IOException {

    super( request );

    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {

      InputStream inputStream = request.getInputStream();
      if( inputStream != null ) {
        bufferedReader = new BufferedReader(
          new InputStreamReader( inputStream ) );
        char[] charBuffer = new char[ 256 ];
        int bytesRead = -1;
        while( ( bytesRead = bufferedReader.read( charBuffer ) ) > 0 ) {
          stringBuilder.append( charBuffer, 0, bytesRead );
        }
      }
      else {
        stringBuilder.append( "" );
      }

    }
    catch( IOException ex ) {
      throw ex;
    }

    finally {
      if( bufferedReader != null ) {
        try {
          bufferedReader.close();
        }
        catch( IOException ex ) {
          throw ex;
        }
      }
    }
    requestData = stringBuilder.toString();
  }


  @Override
  public ServletInputStream getInputStream() throws IOException {

    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
      requestData.getBytes() );

    ServletInputStream servletInputStream = new ServletInputStream() {


      public int read() throws IOException {
        return byteArrayInputStream.read();
      }
    };

    return servletInputStream;
  }


  @Override
  public BufferedReader getReader() throws IOException {

    return new BufferedReader( new InputStreamReader( this.getInputStream() ) );

  }


  /**
   * returns the request data extracted from the request
   * 
   * @return
   */
  public String getRequestData() {
    return this.requestData;
  }

}
