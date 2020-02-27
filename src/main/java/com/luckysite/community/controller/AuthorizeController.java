package com.luckysite.community.controller;

import com.luckysite.community.dto.AccessTokenDTO;
import com.luckysite.community.dto.GithubUser;
import com.luckysite.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @aothor dongyingjie
 * @date 2020/2/25  15:03
 **/

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientISecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    /**
     *获取code github 回调
     * @param code github 返回code
     * @param state 默认1
     * @return
     */
    @GetMapping("/callback")
    public  String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientISecret);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";

    }
}
