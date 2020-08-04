package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService){
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    public List<Credential> getCredentials(String username){
        Integer userId = userService.getUser(username).getUserId();
        return credentialMapper.getCredentials(userId);
    }

    public int addCredential(Credential credential, String username){
        Integer userId = userService.getUser(username).getUserId();

        //TODO: implement key + password encryption

        if(credential.getCredentialId() == null) {
            return credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUsername(), null, null, userId));
        }else{
            return credentialMapper.updateCredential(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), null, null, userId));
        }
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.deleteCredential(credentialId);
    }
}
