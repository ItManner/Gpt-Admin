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
package me.zhengjie.modules.game.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author LanShao
* @date 2023-05-12
**/
@Entity
@Data
@Table(name="sim_ets_info")
public class SimEtsInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`TMP_ID`")
    @ApiModelProperty(value = "tmpId")
    private Integer tmpId;

    @Column(name = "`TMP_NAME`")
    @ApiModelProperty(value = "tmpName")
    private String tmpName;

    @Column(name = "`USER_QQ`")
    @ApiModelProperty(value = "userQq")
    private String userQq;

    @Column(name = "`GROUP_NUM`")
    @ApiModelProperty(value = "groupNum")
    private Integer groupNum;

    @Column(name = "`DEPT_ID`")
    @ApiModelProperty(value = "deptId")
    private Integer deptId;

    public void copy(SimEtsInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
