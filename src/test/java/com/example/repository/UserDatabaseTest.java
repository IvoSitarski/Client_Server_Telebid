package com.example.repository;

import org.example.repository.UserDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import org.example.Models.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDatabaseTest {

    private UserDatabase userDatabase;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        userDatabase = new UserDatabase();
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDatabase.setConnection(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testFindByEmail_UserExists() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn("test@example.com");
        when(resultSet.getString("first_name")).thenReturn("Test");
        when(resultSet.getString("last_name")).thenReturn("User");
        when(resultSet.getString("password")).thenReturn("password");

        User user = userDatabase.findByEmail("test@example.com");

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
    }

    @Test
    void testFindByEmail_UserNotExists() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        User user = userDatabase.findByEmail("nonexistent@example.com");

        assertNull(user);
    }

    @Test
    void testSave() throws SQLException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password");

        userDatabase.save(user);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testUpdate() throws SQLException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password");

        userDatabase.update(user);

        verify(preparedStatement, times(1)).executeUpdate();
    }
}


