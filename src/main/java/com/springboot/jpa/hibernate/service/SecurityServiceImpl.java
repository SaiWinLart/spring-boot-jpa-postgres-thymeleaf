//package com.springboot.jpa.hibernate.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SecurityServiceImpl implements SecurityService{
//  //  @Autowired
//    private AuthenticationManager authenticationManager;
//
//   // @Autowired
//    private UserDetailsService userDetailsService;
//
//    public SecurityServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
//		super();
//		this.authenticationManager = authenticationManager;
//		this.userDetailsService = userDetailsService;
//	}
//
//	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
//
//    public boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || AnonymousAuthenticationToken.class.
//            isAssignableFrom(authentication.getClass())) {
//            return false;
//        }
//        return authentication.isAuthenticated();
//    }
//
//	@Override
//	public void autoLogin(String username, String password) {
//		// TODO Auto-generated method stub
//		
//	}
//
////    @Override
////    public void autoLogin(String username, String password) {
////        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
////
////        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
////
////        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
////            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
////            logger.debug(String.format("Auto login %s successfully!", username));
////        }
////    }
// 
//// @Override
////   @Transactional 
////   public UserDetails loadUserByUsername(String username) {
////       Optional<User> userOption = userDetailsService.findUserByUsername(username);
////       User user = userOption.get();
////       if (user == null) throw new UsernameNotFoundException(username);
////     
////       Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
////       for (Role role : user.getRoles()){
////           grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
////       }
////
////       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
////   }
//
//}
// 
