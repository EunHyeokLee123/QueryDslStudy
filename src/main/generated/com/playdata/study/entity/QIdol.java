package com.playdata.study.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIdol is a Querydsl query type for Idol
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdol extends EntityPathBase<Idol> {

    private static final long serialVersionUID = -795861413L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIdol idol = new QIdol("idol");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath gender = createString("gender");

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idolName = createString("idolName");

    public QIdol(String variable) {
        this(Idol.class, forVariable(variable), INITS);
    }

    public QIdol(Path<? extends Idol> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIdol(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIdol(PathMetadata metadata, PathInits inits) {
        this(Idol.class, metadata, inits);
    }

    public QIdol(Class<? extends Idol> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
    }

}

