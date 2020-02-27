package com.luckysite.community.provider;

import com.alibaba.fastjson.JSON;
import com.luckysite.community.dto.AccessTokenDTO;
import com.luckysite.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @aothor dongyingjie
 * @date 2020/2/25  20:11
 **/
@Component//初始化到spring上下文
public class GithubProvider {

    /**
     * 根据Access_Token获取用户信息
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                //返回实例 access_token=a5eef5cc0ea4241af4566d6d91049b477e46090f&scope=user&token_type=bearer
                String[] split = string.split("&");
                String access_token = split[0].split("=")[1];
                return  access_token;
            }catch (Exception e){
                e.printStackTrace();
            }
        return  null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client =  new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //解析string 类型对象为实体类对象
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return  githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
