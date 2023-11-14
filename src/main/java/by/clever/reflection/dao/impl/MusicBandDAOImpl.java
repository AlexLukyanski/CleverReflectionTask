package by.clever.reflection.dao.impl;

import by.clever.reflection.dao.MusicBandDAO;
import by.clever.reflection.dao.connectionpool.ConnectionPool;
import by.clever.reflection.dao.connectionpool.ConnectionPoolException;
import by.clever.reflection.dao.constant.DBMusicBandColumnName;
import by.clever.reflection.dao.exception.DAOException;
import by.clever.reflection.entity.MusicBand;
import by.clever.reflection.entity.constant.MusicGenre;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class MusicBandDAOImpl implements MusicBandDAO {

    private final static String INSERT_NEW_BAND_SQL = "INSERT INTO music_band " +
            "(mb_name,mb_genre,mb_creationdate,mb_phonenumber,mb_email) VALUES (?,?,?,?,?) RETURNING mb_id";

    @Override
    public UUID save(MusicBand band) {

        UUID id;

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_BAND_SQL)) {

            preparedStatement.setString(1, band.getName());
            preparedStatement.setString(2, band.getGenre().toString());
            preparedStatement.setDate(3, Date.valueOf(band.getCreationDate()));
            preparedStatement.setString(4, band.getWorkPhoneNumber());
            preparedStatement.setString(5, band.getWorkEmail());

            boolean insertionResult = preparedStatement.execute();

            if (!insertionResult) {
                throw new DAOException("Save music band failed");
            }

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                id = UUID.fromString(resultSet.getString(1));
            } else {
                throw new DAOException("Generation UUID failed");
            }

            return id;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_BAND_SQL = "SELECT " +
            "mb_id,mb_name,mb_genre,mb_creationdate,mb_phonenumber,mb_email " +
            "FROM music_band WHERE mb_id=? AND mb_isdeleted=false";

    @Override
    public Optional<MusicBand> readByID(UUID id) {

        Optional<MusicBand> band;

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BAND_SQL)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                band = setMusicBand(resultSet);
            } else {
                throw new DAOException("Music band not found");
            }

            return band;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String UPDATE_BAND_SQL = "UPDATE music_band SET" +
            " mb_name=?,mb_genre=?,mb_creationdate=?,mb_phonenumber=?,mb_email=? " +
            "WHERE mb_id=?";

    @Override
    public UUID update(MusicBand band) {

        UUID id;

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BAND_SQL)) {

            preparedStatement.setString(1, band.getName());
            preparedStatement.setString(2, band.getGenre().toString());
            preparedStatement.setDate(3, Date.valueOf(band.getCreationDate()));
            preparedStatement.setString(4, band.getWorkPhoneNumber());
            preparedStatement.setString(5, band.getWorkEmail());
            preparedStatement.setObject(6, band.getId());

            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult == 0) {
                throw new DAOException("Update music band failed");
            }

            return band.getId();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String DELETE_BAND_SQL = "UPDATE music_band SET mb_isdeleted=true WHERE mb_id=?";

    @Override
    public void delete(UUID id) {

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BAND_SQL)) {

            preparedStatement.setObject(1, id);
            int deletionResult = preparedStatement.executeUpdate();

            if (deletionResult == 0) {
                throw new DAOException("Deletion music band failed");
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private Optional<MusicBand> setMusicBand(ResultSet resultSet) throws SQLException {

        MusicBand band = new MusicBand();
        band.setId(UUID.fromString(resultSet.getString(DBMusicBandColumnName.ID_COLUMN)));
        band.setName(resultSet.getString(DBMusicBandColumnName.NAME_COLUMN));
        band.setGenre(MusicGenre.valueOf(resultSet.getString(DBMusicBandColumnName.GENRE_COLUMN)));
        band.setCreationDate(resultSet.getDate(DBMusicBandColumnName.CREATION_DATE_COLUMN).toLocalDate());
        band.setWorkPhoneNumber(resultSet.getString(DBMusicBandColumnName.PHONE_NUMBER_COLUMN));
        band.setWorkEmail(resultSet.getString(DBMusicBandColumnName.EMAIL_COLUMN));

        return Optional.of(band);
    }
}
