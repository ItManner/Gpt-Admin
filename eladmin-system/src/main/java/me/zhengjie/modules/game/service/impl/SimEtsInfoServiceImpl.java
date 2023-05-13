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
package me.zhengjie.modules.game.service.impl;

import me.zhengjie.modules.game.domain.SimEtsInfo;
import me.zhengjie.modules.game.service.SimEtsInfoService;
import me.zhengjie.modules.game.service.dto.SimEtsInfoQueryCriteria;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.game.repository.SimEtsInfoRepository;
import me.zhengjie.modules.game.service.dto.SimEtsInfoDto;
import me.zhengjie.modules.game.service.mapstruct.SimEtsInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author LanShao
* @date 2023-05-12
**/
@Service
@RequiredArgsConstructor
public class SimEtsInfoServiceImpl implements SimEtsInfoService {

    private final SimEtsInfoRepository simEtsInfoRepository;
    private final SimEtsInfoMapper simEtsInfoMapper;

    @Override
    public Map<String,Object> queryAll(SimEtsInfoQueryCriteria criteria, Pageable pageable){
        Page<SimEtsInfo> page = simEtsInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(simEtsInfoMapper::toDto));
    }

    @Override
    public List<SimEtsInfoDto> queryAll(SimEtsInfoQueryCriteria criteria){
        return simEtsInfoMapper.toDto(simEtsInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SimEtsInfoDto findById(Integer id) {
        SimEtsInfo simEtsInfo = simEtsInfoRepository.findById(id).orElseGet(SimEtsInfo::new);
        ValidationUtil.isNull(simEtsInfo.getId(),"SimEtsInfo","id",id);
        return simEtsInfoMapper.toDto(simEtsInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimEtsInfoDto create(SimEtsInfo resources) {
        return simEtsInfoMapper.toDto(simEtsInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SimEtsInfo resources) {
        SimEtsInfo simEtsInfo = simEtsInfoRepository.findById(resources.getId()).orElseGet(SimEtsInfo::new);
        ValidationUtil.isNull( simEtsInfo.getId(),"SimEtsInfo","id",resources.getId());
        simEtsInfo.copy(resources);
        simEtsInfoRepository.save(simEtsInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            simEtsInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SimEtsInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SimEtsInfoDto simEtsInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" tmpId",  simEtsInfo.getTmpId());
            map.put(" tmpName",  simEtsInfo.getTmpName());
            map.put(" userQq",  simEtsInfo.getUserQq());
            map.put(" groupNum",  simEtsInfo.getGroupNum());
            map.put(" deptId",  simEtsInfo.getDeptId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
