package org.training.food.tracker.dao.util;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;


    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new ConnectionFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
