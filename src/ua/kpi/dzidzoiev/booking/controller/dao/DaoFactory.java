package ua.kpi.dzidzoiev.booking.controller.dao;

/**
 * Created by midnight coder on 17-Mar-15.
 */
public class DaoFactory {
    public static final int PROVIDER_MYSQL = 3500001;
    public static final int PROVIDER_JPA = 3500002;

    public static final int ENTITIY_CITY = 7500001;

    private DaoFactory(){};

    public Dao getDao(int provider, int entitiy) {
        switch (provider + entitiy) {
            case PROVIDER_MYSQL + ENTITIY_CITY:
                return new MySqlCityDaoImpl();
            case PROVIDER_JPA + ENTITIY_CITY:
                return new JpaCityDaoImpl();
            default:
                throw new IllegalStateException("no such code");
        }
    }

    public static DaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final DaoFactory INSTANCE = new DaoFactory();
    }
}
