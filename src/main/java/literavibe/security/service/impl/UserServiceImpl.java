package literavibe.security.service.impl;

import literavibe.model.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.UserDto;

import literavibe.model.entities.Media;
import literavibe.model.entities.Role;
import literavibe.model.entities.User;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.model.exceptions.UserException;
import literavibe.respository.MediaRepository;
import literavibe.respository.RoleRepository;
import literavibe.respository.UserRepository;
import literavibe.security.config.BeanConfig;
import literavibe.security.domain.JwtAuthentication;
import literavibe.security.service.UserService;
import literavibe.utils.FindUtils;

import java.util.*;

import static literavibe.security.service.impl.AuthServiceCommon.getAuthInfo;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BeanConfig passwordEncoder;
    private final ModelMapper mapper;
    private final MediaRepository mediaRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BeanConfig passwordEncoder, ModelMapper mapper,
                           RoleRepository roleRepository,
                           MediaRepository mediaRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.mediaRepository = mediaRepository;
    }


    Role findRole(String name) throws NotFoundException {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isEmpty()) {
            throw new NotFoundException("Couldn't find role " + name);
        }
        return roleOptional.get();
    }

    @Override
    public ResponseEntity<IdDto> postUser(UserDto userDto) throws NotFoundException {
        User user = mapper.map(userDto, User.class);
        user.setId(null);
        user.setLogin(userDto.getLogin());
        Role userRole = findRole("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new IdDto().id(savedUser.getId()));
    }

    @Override
    public ResponseEntity<UserDto> getUserProfile() throws Exception {
        JwtAuthentication principal = getAuthInfo();
        if (principal == null) {
            throw new AuthException("Not authorized yet");
        }
        User user = FindUtils.findUser(userRepository, principal.getLogin());
        UserDto userDto = mapper.map(user, UserDto.class);
//        UserInfo userInfo = userInfoRepository.findById(user.getId()).orElseThrow(
//                () -> new UserException("Couldn't find user info"));
//        UserProfileDto userDto = mapper.map(userInfo, UserProfileDto.class);
//        userDto.setId(user.getId());
        return ResponseEntity.ok(userDto);
    }

//    @Override
//    public ResponseEntity<List<UserProfileDto>> getUserProfile(String login, Integer limit) throws Exception {
//        if (limit == null) {
//            limit = 0;
//        }
//        List<UserProfileDto> userProfileDtos = new ArrayList<>();
//        List<User> users = userRepository.findByIdContaining(login, limit);
//        if (users.isEmpty()) {
//            throw new NotFoundException("Couldn't find users with substring: " + login);
//        }
//        for (User user : users) {
//            getUserProfileInfo(userProfileDtos, user);
//        }
//
//        return ResponseEntity.ok(userProfileDtos);
//    }

//    private void getUserProfileInfo(List<UserProfileDto> userProfileDtos, User user) {
//        Optional<UserInfo> userInfo = userInfoRepository.findById(user.getId());
//        UserProfileDto userProfileDto;
//        if (userInfo.isPresent()) {
//            userProfileDto = mapper.map(userInfo, UserProfileDto.class);
//        } else {
//            userProfileDto = new UserProfileDto();
//        }
//        userProfileDto.setId(user.getId());
//        userProfileDtos.add(userProfileDto);
//    }


    @Override
    public ResponseEntity<IdDto> profileUpdate(UserDto profileDto) throws BadRequestException,
            NotFoundException {
        if (!AuthServiceCommon.checkAuthorities(profileDto.getLogin())) {
            throw new BadRequestException("No rights");
        }
        User user = FindUtils.findUser(userRepository, profileDto.getLogin());
        user.setEmail(profileDto.getEmail());
        user.setDisplayName(profileDto.getDisplayName());
        userRepository.save(user);
        Media media = null;
        Long oldMediaId = null;
        Long mediaId = profileDto.getMediaId();
        if (mediaId != null) {
            media = FindUtils.findMedia(mediaRepository, mediaId);
        }
        if (oldMediaId != null) {
            if (media == null || !Objects.equals(media.getId(), oldMediaId)) {
                mediaRepository.deleteById(oldMediaId);
            }
        }
        return ResponseEntity.ok(new IdDto().id(user.getId()));
    }

//    private static void setUserInfo(UserProfileDto profileDto, UserInfo userInfo) {
//        userInfo.setInfo(profileDto.getInfo());
//        userInfo.setVkLink(profileDto.getVkLink());
//        userInfo.setTgLink(profileDto.getTgLink());
//        userInfo.setDisplayName(profileDto.getDisplayName());
//    }

    @Override
    public ResponseEntity<IdDto> profilePost(UserDto profileDto) throws BadRequestException, NotFoundException,
            UserException {
        if (!AuthServiceCommon.checkAuthorities(profileDto.getLogin())) {
            throw new BadRequestException("No rights");
        }
//        UserInfo userInfo = mapper.map(profileDto, UserInfo.class);
        User user = FindUtils.findUser(userRepository, profileDto.getLogin());
//        if (user.getUserInfo() != null) {
//            throw new UserException("User info already exists");
//        }
//        userInfo.setUser(user);
//        UserInfo newUser = userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new IdDto().id(user.getId()));
    }

    public ResponseEntity<IdDto> updateUserPassword(UserDto userDto) throws NotFoundException, BadRequestException {
        if (!AuthServiceCommon.checkAuthorities(userDto.getLogin())) {
            throw new BadRequestException("No rights");
        }
        User user = FindUtils.findUser(userRepository, userDto.getLogin());
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new IdDto().id(savedUser.getId()));
    }
}


