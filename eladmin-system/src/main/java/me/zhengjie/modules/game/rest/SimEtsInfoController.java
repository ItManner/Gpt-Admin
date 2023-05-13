/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.game.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.game.domain.SimEtsInfo;
import me.zhengjie.modules.game.service.SimEtsInfoService;
import me.zhengjie.modules.game.service.dto.SimEtsInfoQueryCriteria;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author LanShao
* @date 2023-05-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "EtsInfo管理")
@RequestMapping("/api/simEtsInfo")
public class SimEtsInfoController {

    private final SimEtsInfoService simEtsInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('simEtsInfo:list')")
    public void exportSimEtsInfo(HttpServletResponse response, SimEtsInfoQueryCriteria criteria) throws IOException {
        simEtsInfoService.download(simEtsInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询EtsInfo")
    @ApiOperation("查询EtsInfo")
    @PreAuthorize("@el.check('simEtsInfo:list')")
    public ResponseEntity<Object> querySimEtsInfo(SimEtsInfoQueryCriteria criteria, Pageable pageable){
        criteria.setDeptId(String.valueOf(SecurityUtils.getCurrentUserId()));
        return new ResponseEntity<>(simEtsInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增EtsInfo")
    @ApiOperation("新增EtsInfo")
    @PreAuthorize("@el.check('simEtsInfo:add')")
    public ResponseEntity<Object> createSimEtsInfo(@Validated @RequestBody SimEtsInfo resources){
        return new ResponseEntity<>(simEtsInfoService.create(resources),HttpStatus.CREATED);
    }


    @PutMapping
    @Log("修改EtsInfo")
    @ApiOperation("修改EtsInfo")
    @PreAuthorize("@el.check('simEtsInfo:edit')")
    public ResponseEntity<Object> updateSimEtsInfo(@Validated @RequestBody SimEtsInfo resources){
        simEtsInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除EtsInfo")
    @ApiOperation("删除EtsInfo")
    @PreAuthorize("@el.check('simEtsInfo:del')")
    public ResponseEntity<Object> deleteSimEtsInfo(@RequestBody Integer[] ids) {
        simEtsInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
