package com.eptonic.photocollage.model;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.eptonic.photocollage.model.PhotoStickerSaveData;

import com.eptonic.photocollage.model.PhotoStickerSaveDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig photoStickerSaveDataDaoConfig;

    private final PhotoStickerSaveDataDao photoStickerSaveDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        photoStickerSaveDataDaoConfig = daoConfigMap.get(PhotoStickerSaveDataDao.class).clone();
        photoStickerSaveDataDaoConfig.initIdentityScope(type);

        photoStickerSaveDataDao = new PhotoStickerSaveDataDao(photoStickerSaveDataDaoConfig, this);

        registerDao(PhotoStickerSaveData.class, photoStickerSaveDataDao);
    }
    
    public void clear() {
        photoStickerSaveDataDaoConfig.clearIdentityScope();
    }

    public PhotoStickerSaveDataDao getPhotoStickerSaveDataDao() {
        return photoStickerSaveDataDao;
    }

}