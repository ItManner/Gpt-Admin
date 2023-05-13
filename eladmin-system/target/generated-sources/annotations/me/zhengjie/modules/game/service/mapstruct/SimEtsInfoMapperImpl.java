package me.zhengjie.modules.game.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.modules.game.domain.SimEtsInfo;
import me.zhengjie.modules.game.service.dto.SimEtsInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-13T00:10:55+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_301 (Oracle Corporation)"
)
@Component
public class SimEtsInfoMapperImpl implements SimEtsInfoMapper {

    @Override
    public SimEtsInfo toEntity(SimEtsInfoDto dto) {
        if ( dto == null ) {
            return null;
        }

        SimEtsInfo simEtsInfo = new SimEtsInfo();

        simEtsInfo.setId( dto.getId() );
        simEtsInfo.setTmpId( dto.getTmpId() );
        simEtsInfo.setTmpName( dto.getTmpName() );
        simEtsInfo.setUserQq( dto.getUserQq() );
        simEtsInfo.setGroupNum( dto.getGroupNum() );
        simEtsInfo.setDeptId( dto.getDeptId() );

        return simEtsInfo;
    }

    @Override
    public SimEtsInfoDto toDto(SimEtsInfo entity) {
        if ( entity == null ) {
            return null;
        }

        SimEtsInfoDto simEtsInfoDto = new SimEtsInfoDto();

        simEtsInfoDto.setId( entity.getId() );
        simEtsInfoDto.setTmpId( entity.getTmpId() );
        simEtsInfoDto.setTmpName( entity.getTmpName() );
        simEtsInfoDto.setUserQq( entity.getUserQq() );
        simEtsInfoDto.setGroupNum( entity.getGroupNum() );
        simEtsInfoDto.setDeptId( entity.getDeptId() );

        return simEtsInfoDto;
    }

    @Override
    public List<SimEtsInfo> toEntity(List<SimEtsInfoDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SimEtsInfo> list = new ArrayList<SimEtsInfo>( dtoList.size() );
        for ( SimEtsInfoDto simEtsInfoDto : dtoList ) {
            list.add( toEntity( simEtsInfoDto ) );
        }

        return list;
    }

    @Override
    public List<SimEtsInfoDto> toDto(List<SimEtsInfo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SimEtsInfoDto> list = new ArrayList<SimEtsInfoDto>( entityList.size() );
        for ( SimEtsInfo simEtsInfo : entityList ) {
            list.add( toDto( simEtsInfo ) );
        }

        return list;
    }
}
