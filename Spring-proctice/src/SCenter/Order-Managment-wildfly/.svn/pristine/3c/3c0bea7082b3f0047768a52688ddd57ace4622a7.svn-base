package com.AL.service.impl;

import org.springframework.stereotype.Component;

import com.AL.V.beans.entity.BundleBean;
import com.AL.Vdao.dao.BundleDao;
import com.AL.Vdao.logical.dao.impl.BundleDaoImpl;
import com.AL.vm.service.BundleService;

@Component
public class BundleServiceImpl implements BundleService
{

    public BundleServiceImpl()
    {
        super();
    }

    public BundleBean findBundleById( String id )
    {
        BundleDao dao = new BundleDaoImpl();
        return dao.findBundleById( id );
    }

    public BundleBean findBundleById( Long id )
    {
        BundleDao dao = new BundleDaoImpl();
        return dao.findBundleById( String.valueOf( id.longValue() ) );
    }

}
