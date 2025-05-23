package com.playdata.study.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = 1096670396L;

    public static final QGroup group = new QGroup("group1");

    public final ListPath<Album, QAlbum> albums = this.<Album, QAlbum>createList("albums", Album.class, QAlbum.class, PathInits.DIRECT2);

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Idol, QIdol> idols = this.<Idol, QIdol>createList("idols", Idol.class, QIdol.class, PathInits.DIRECT2);

    public QGroup(String variable) {
        super(Group.class, forVariable(variable));
    }

    public QGroup(Path<? extends Group> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroup(PathMetadata metadata) {
        super(Group.class, metadata);
    }

}

