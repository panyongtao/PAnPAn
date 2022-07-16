package pan.service;


import pan.domain.ResponseResult;
import pan.domain.User;

public interface LoginServcie {
    ResponseResult login(User user);

    ResponseResult logout();

}
