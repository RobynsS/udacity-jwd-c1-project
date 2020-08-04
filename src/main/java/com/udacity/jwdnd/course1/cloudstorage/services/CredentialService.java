package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return credentialMapper.getCredentials(userId);
    }

    public String getDecryptedPassword(int credentialId){
        Credential credential = credentialMapper.getCredential(credentialId);
        String encodedKey = credential.getKey();
        String encryptedPassword = credential.getPassword();

        return encryptionService.decryptValue(encryptedPassword, encodedKey);
    }

    public int addCredential(Credential credential, String username){
        Integer userId = userService.getUser(username).getUserId();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        if(credential.getCredentialId() == null) {
            return credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId));
        }else{
            return credentialMapper.updateCredential(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId));
        }
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.deleteCredential(credentialId);
    }
}
