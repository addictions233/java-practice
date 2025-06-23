//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.one.filter;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XssFilter implements Filter {
    public List<String> excludes = new ArrayList();
    public boolean enabled = false;

    public XssFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String tempExcludes = filterConfig.getInitParameter("excludes");
        String tempEnabled = filterConfig.getInitParameter("enabled");
        if (!StringUtils.isEmpty(tempExcludes)) {
            String[] url = tempExcludes.split(",");

            this.excludes.addAll(Arrays.asList(url));
        }

        if (!StringUtils.isEmpty(tempEnabled)) {
            this.enabled = Boolean.parseBoolean(tempEnabled);
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        if (this.handleExcludeURL(req, resp)) {
            chain.doFilter(request, response);
        } else {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest)request);
            chain.doFilter(xssRequest, response);
        }
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (!this.enabled) {
            return true;
        } else if (this.excludes != null && !this.excludes.isEmpty()) {
            String url = request.getServletPath();
            Iterator var4 = this.excludes.iterator();

            Matcher m;
            do {
                if (!var4.hasNext()) {
                    return false;
                }

                String pattern = (String)var4.next();
                Pattern p = Pattern.compile("^" + pattern);
                m = p.matcher(url);
            } while(!m.find());

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroy() {
    }
}
