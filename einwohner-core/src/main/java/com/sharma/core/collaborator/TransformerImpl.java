package com.sharma.core.collaborator;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TransformerImpl implements Transformer{

    private MapperFactory mapperFactory;

    @PostConstruct
    public void init(){
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    @Override
    public <SRC,DEST> DEST transform(SRC source, Class<DEST> destinationClass) {
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(source, destinationClass);
    }

}
