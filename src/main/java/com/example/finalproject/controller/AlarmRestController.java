package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.AlarmDto;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarms")
@AllArgsConstructor
@Api(tags = "알람")
public class AlarmRestController {

    private final AlarmService service;

    @ApiOperation(value = "알람 확인")
    @GetMapping()
    public Response<Page<AlarmDto>> alarmPage(@PageableDefault(size=20, sort="createdAt", direction = Sort.Direction.DESC)
                                              Pageable pageable) {
        Page<AlarmDto> page = service.alarmPage(pageable);
        return Response.success(page);
    }
}
