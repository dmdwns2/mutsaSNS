package com.example.finalproject.service;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.AlarmDto;
import com.example.finalproject.enums.AlarmType;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlarmServiceTest {

    @Mock
    private AlarmRepository alarmRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AlarmService alarmService;

    private final Long userId = 1L;
    private final String userName = "messi";
    private final String password = "password";
    private final UserEntity user = UserEntity.builder()
            .id(userId)
            .userName(userName)
            .password(password)
            .build();

    @Test
    public void 알람테스트() {
        // given
        AlarmEntity alarmEntity1 = AlarmEntity.builder()
                .id(1L)
                .alarmType(AlarmType.NEW_COMMENT_ON_POST)
                .fromUserId(1L)
                .targetId(1L)
                .text("new comment on post")
                .build();
        AlarmEntity alarmEntity2 = AlarmEntity.builder()
                .id(2L)
                .alarmType(AlarmType.NEW_LIKE_ON_COMMENT)
                .fromUserId(2L)
                .targetId(2L)
                .text("new like on comment")
                .build();
        Pageable pageable = PageRequest.of(0, 10);
        Page<AlarmEntity> page = new PageImpl<>(List.of(alarmEntity1, alarmEntity2), pageable, 2);

        when(alarmRepository.findAlarmEntitiesByUser(user,pageable)).thenReturn(page);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(user));
        // when
        Page<AlarmDto> result = alarmService.alarmPage(pageable, userName);

        // then
        assertThat(result.getContent()).hasSize(2);

        AlarmDto alarmDto1 = result.getContent().get(0);
        assertThat(alarmDto1.getId()).isEqualTo(1L);
        assertThat(alarmDto1.getAlarmType()).isEqualTo(AlarmType.NEW_COMMENT_ON_POST);
        assertThat(alarmDto1.getFromUserId()).isEqualTo(1L);
        assertThat(alarmDto1.getTargetId()).isEqualTo(1L);
        assertThat(alarmDto1.getText()).isEqualTo("new comment on post");

        AlarmDto alarmDto2 = result.getContent().get(1);
        assertThat(alarmDto2.getId()).isEqualTo(2L);
        assertThat(alarmDto2.getAlarmType()).isEqualTo(AlarmType.NEW_LIKE_ON_COMMENT);
        assertThat(alarmDto2.getFromUserId()).isEqualTo(2L);
        assertThat(alarmDto2.getTargetId()).isEqualTo(2L);
        assertThat(alarmDto2.getText()).isEqualTo("new like on comment");
    }
}