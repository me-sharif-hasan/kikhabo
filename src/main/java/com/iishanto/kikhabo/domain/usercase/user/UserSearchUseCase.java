package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserSearchUseCase implements UseCase<List<User>, String> {
    UserDataSource userDataSource;
    @Override
    public List<User> execute(String keyword) throws Exception, GlobalServerException {
        return userDataSource.searchUser(keyword);
    }
}
