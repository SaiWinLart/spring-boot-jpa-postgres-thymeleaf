// package com.springboot.jpa.hibernate;
// import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
// public class CustomHandlerInterceptor implements HandlerInterceptor {
//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//         // Pre-processing logic (before the controller method is invoked)
//         return true; // Return false to stop further processing
//     }
//
//     @Override
//     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//         // Post-processing logic (after the controller method is invoked but before view rendering)
//     }
//
//     @Override
//     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    	  int httpStatus = response.getStatus();
//          String reasonPhrase = org.springframework.http.HttpStatus.valueOf(httpStatus).getReasonPhrase();
//
//          // Now you can use 'httpStatus' and 'reasonPhrase' as needed
//          // (e.g., log them, process them, etc.)
//          System.out.println(".......................HTTP Status Code: " + httpStatus);
//          System.out.println("................Reason Phrase: " + reasonPhrase);
//     }
// }
