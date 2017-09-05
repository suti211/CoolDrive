package service;

import dto.Share;
import dto.Status;
import dto.Token;
import dto.UserFile;

import java.util.List;

public interface PermissionService {
    Status add(Share share);

    Status remove(Share share);

    Status changeAccess(Share share);

    List<UserFile> sharedWithMe(Token token);

    List<UserFile> sharedFiles(Token token);

    List<UserFile> sharedFolder(Token token);

    List<Share> sharedWith(Token token);
}
