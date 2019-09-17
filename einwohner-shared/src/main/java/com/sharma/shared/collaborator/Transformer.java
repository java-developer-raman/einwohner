package com.sharma.shared.collaborator;

public interface Transformer {
    <SRC,DEST> DEST transform(SRC source, Class<DEST> destionationClass);
}
