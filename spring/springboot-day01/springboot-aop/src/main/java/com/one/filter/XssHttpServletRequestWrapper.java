//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.one.filter;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author one
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return super.getParameterValues(name);
        } else {
            int length = values.length;
            String[] escapseValues = new String[length];

            for(int i = 0; i < length; ++i) {
                escapseValues[i] = (values[i]).trim();
            }

            return escapseValues;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (!this.isJsonRequest()) {
            return super.getInputStream();
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(super.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            if (StringUtils.isEmpty(json)) {
                return super.getInputStream();
            } else {
                json = json.trim();
                final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
                return new ServletInputStream() {
                    @Override
                    public boolean isFinished() {
                        return true;
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setReadListener(ReadListener readListener) {
                    }

                    @Override
                    public int read() throws IOException {
                        return bis.read();
                    }
                };
            }
        }
    }

    public boolean isJsonRequest() {
        String header = super.getHeader("Content-Type");
        return "application/json".equalsIgnoreCase(header) || "application/json;charset=UTF-8".equalsIgnoreCase(header);
    }
}
