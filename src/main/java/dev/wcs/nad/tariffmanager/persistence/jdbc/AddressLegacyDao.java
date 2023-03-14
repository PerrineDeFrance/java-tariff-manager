package dev.wcs.nad.tariffmanager.persistence.jdbc;

import dev.wcs.nad.tariffmanager.persistence.entity.Address;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class AddressLegacyDao {

    private final DataSource dataSource;

    
    public AddressLegacyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Address> getByIdJava7Syntax(long id) {
        // Challenge: Add the retrieval of the Address ResultSet and the Mapping to an instance of Address here.

       
        try (Connection connection = dataSource.getConnection();
           
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ADRESS WHERE ID=?")) {
            stmt.setLong(1, id);
           
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    
                    String name = resultSet.getString(1);
                    return Optional.of(new VICustomer(String.valueOf(id), name, "", LocalDate.now(), LocalDate.now()));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.empty();
    }
      
    }




