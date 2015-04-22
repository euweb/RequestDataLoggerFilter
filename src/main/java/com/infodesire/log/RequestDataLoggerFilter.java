// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * filter which logs
 * 
 * @author eugen
 *
 */
public class RequestDataLoggerFilter implements Filter {


  private static final Logger log = Logger
    .getLogger( RequestDataLoggerFilter.class.getName() );


  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void doFilter( ServletRequest request, ServletResponse response,
    FilterChain chain ) throws IOException, ServletException {

    if( request instanceof HttpServletRequest ) {

      RequestDataWrapper myRequestWrapper = new RequestDataWrapper(
        (HttpServletRequest) request );

      String requestData = myRequestWrapper.getRequestData();
      String clientIP = myRequestWrapper.getRemoteHost();
      String uri = myRequestWrapper.getRequestURI();

      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
      String dateString = format.format( date );

      log.finer( "= start ===================" );
      log.finer( "date: " + dateString );
      log.finer( "client: " + clientIP );
      log.finer( "url: " + uri );
      log.finer( "message:" );
      log.finer( requestData );
      log.finer( "= end =====================" );

      //      System.out.println( "= start ===================" );
      //      System.out.println( "date: " + dateString );
      //      System.out.println( "client: " + clientIP );
      //      System.out.println( "url: " + uri );
      //      System.out.println( "message:" );
      //      System.out.println( requestData );
      //      System.out.println( "= end =====================" );

      chain.doFilter( myRequestWrapper, response );
    }
    else {

      // non http request, can not create wrapper

    }

  }


  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#destroy()
   */
  @Override
  public void destroy() {
    // nothing to do
  }


  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init( FilterConfig arg0 ) throws ServletException {
    // nothing to do
  }

}
