package com.sharma.core.collaborator;

public interface Transformer {
    <SRC,DEST> DEST transform(SRC source, Class<DEST> destionationClass);
}
