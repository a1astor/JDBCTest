package com.noirix.repository.impl;

import com.noirix.domain.Car;
import com.noirix.exception.NoSuchEntityException;
import com.noirix.repository.CarsRepository;
import com.noirix.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsRepositoryImpl implements CarsRepository {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String OWNER = "owner";
    public static final String PRODUCTION_DATE = "PRODUCTION_DATE";
    public static final String PRICE = "price";
    public static final String DEALER_ID = "dealer_id";
    public static final String MODEL = "model";

    @Override
    public List<Car> getAllDealerCars(Long dealerID) {
        final String sql = "select * from cars where dealer_id = ?";
        return getCarsBySQL(sql);
    }

    @Override
    public List<Car> getMostExpensiveCars() {
        final String sql = "select * from cars where price = (select max(price) from cars group by price)";
        return getCarsBySQL(sql);
    }

    @Override
    public List<Car> findAll() {
        final String findAllQuery = "select * from cars order by id desc";
        return getCarsBySQL(findAllQuery);
    }

    private List<Car> getCarsBySQL(String findAllQuery) {
        List<Car> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findAllQuery);

            //Row mapping
            while (rs.next()) {
                Car car = new Car();
                setCarAttributesFromResultSet(rs, car);
                result.add(car);
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private void setCarAttributesFromResultSet(ResultSet rs, Car car) throws SQLException {
        car.setId(rs.getLong(rs.getString(ID)));
        car.setName(rs.getString(rs.getString(NAME)));
        car.setOwner(rs.getLong(rs.getString(OWNER)));
        car.setDealer_id(rs.getLong(DEALER_ID));
        car.setModel(rs.getString(MODEL));
        car.setProduction_date(rs.getDate(PRODUCTION_DATE));
        car.setPrice(rs.getDouble(PRICE));
    }

    @Override
    public Car findOne(Long id) {
        final String findById = "select * from cars where id = ?";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(findById);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            //Row mapping
            if (rs.next()) {
                Car car = new Car();
                setCarAttributesFromResultSet(rs, car);
                return car;
            }

            throw new NoSuchEntityException("No such user with id:" + id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Car save(Car entity) {
        final String insertQuery = "insert into cars (owner, name, model, production_date, price, dealer_id) " +
                "values (?,?,?,?,?,?)";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(insertQuery);

            PreparedStatement lastInsertId = connection.prepareStatement("SELECT currval('users_id_seq') as last_insert_id;");

            statement.setLong(1, entity.getOwner());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getModel());
            statement.setDate(4, new Date(entity.getProduction_date().getTime()));
            statement.setDouble(5, entity.getPrice());
            statement.setLong(6, entity.getDealer_id());

            statement.executeUpdate();

            Long insertedId;
            ResultSet lastIdResultSet = lastInsertId.executeQuery();
            if (lastIdResultSet.next()) {
                insertedId = lastIdResultSet.getLong("last_insert_id");
            } else {
                throw new RuntimeException("We cannot read sequence last value during User creation!");
            }

            return findOne(insertedId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Car update(Car entity) {
        final String updateQuery = "update cars set owner = ?,name = ?, production_date = ?,dealer_id = ?,price = ?, model = ? where id = ? ";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = DBUtils.getConnection();
            statement = connection.prepareStatement(updateQuery);
            statement.setLong(1, entity.getOwner());
            statement.setString(2, entity.getName());
            statement.setDate(3, new Date(entity.getProduction_date().getTime()));
            statement.setLong(4, entity.getDealer_id());
            statement.setDouble(5, entity.getPrice());
            statement.setString(6, entity.getModel());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
            return findOne(entity.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public void delete(Long id) {
        final String removeQuery = "delete from cars where id = ? ";
        DBUtils.deleteById(id, removeQuery);
    }
}
