package com.cloudbees.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.auth.AuthDescriptor;

public class MainServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    String servers = this.getServletContext().getInitParameter("MEMCACHIER_SERVERS");
    String username = this.getServletContext().getInitParameter("MEMCACHIER_USERNAME");
    String password = this.getServletContext().getInitParameter("MEMCACHIER_PASSWORD");

    PrintWriter out = response.getWriter();
    AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
                          new PlainCallbackHandler(username, password));
    try {
      MemcachedClient mc = new MemcachedClient(new ConnectionFactoryBuilder()
                               .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                               .setAuthDescriptor(ad).build(), AddrUtil.getAddresses(servers));
      String now = "" + System.currentTimeMillis();
			mc.set(now, 3600, "foo");
			boolean cache_is_working = mc.get(now).equals("foo");
      out.println("<p>" + now +
                  " set in the cache? <strong>" + cache_is_working + "</strong></p>");
    } catch (Exception e) {
      out.println(e.toString());
    } finally {
      out.close();
    }
  }

  @Override
  public String getServletInfo() {
    return "MemCachier CloudBees Example";
  }
}
