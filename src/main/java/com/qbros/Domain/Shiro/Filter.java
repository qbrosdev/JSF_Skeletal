package com.qbros.Domain.Shiro;

import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by V.Ghasemi
 * on 12/5/2018.
 */
public class Filter extends ShiroFilter {

    @Override
    public void init(){
        try {
            super.init();
            WebEnvironment env = WebUtils.getRequiredWebEnvironment(this.getServletContext());
            System.out.println(env.getServletContext().getInitParameterNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ServletRequest prepareServletRequest(ServletRequest request, ServletResponse response, FilterChain chain){
        System.out.println("interval sec: "+(((HttpServletRequest) request).getSession().getMaxInactiveInterval()));
        return super.prepareServletRequest(request, response, chain);
    }
}
