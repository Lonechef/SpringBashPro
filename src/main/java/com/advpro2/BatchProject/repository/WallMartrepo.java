package com.advpro2.BatchProject.repository;

import java.io.Serializable;
import com.advpro2.BatchProject.entity.WalMartdata;
import org.springframework.data.jpa.repository.JpaRepository;
//This repo is for WallMartdata
public interface   WallMartrepo extends JpaRepository <WalMartdata, Serializable>{

}

