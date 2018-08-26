package com.A.service.impl;

import org.springframework.stereotype.Component;

import com.A.V.beans.entity.BundleBean;
import com.A.Vdao.dao.BundleDao;
import com.A.Vdao.logical.dao.impl.BundleDaoImpl;
import com.A.vm.service.BundleService;

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
