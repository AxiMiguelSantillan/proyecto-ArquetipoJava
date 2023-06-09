package com.axity.office.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.util.ArrayList;
 
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
 
import com.axity.office.commons.dto.RoleDto;
import com.axity.office.commons.dto.UserDto;
import com.axity.office.commons.enums.ErrorCode;
import com.axity.office.commons.exception.BusinessException;
import com.axity.office.commons.request.PaginatedRequestDto;

/**
 * Class UserServiceTest
 * 
 * @author username@axity.com
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class UserServiceTest
{
  private static final Logger LOG = LoggerFactory.getLogger( UserServiceTest.class );

  @Autowired
  private UserService userService;

  /**
   * Method to validate the paginated search
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testFindUsers()
  {
    var request = new PaginatedRequestDto();
    request.setLimit( 5 );
    request.setOffset( 0 );
    var users = this.userService.findUsers( request );

    LOG.info( "Response: {}", users );

    assertNotNull( users );
    assertNotNull( users.getData() );
    assertFalse( users.getData().isEmpty() );
  }

  /**
   * Method to validate the search by id
   * 
   * @param userId
   */
  @ParameterizedTest
  @ValueSource(ints = { 1 })
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testFind( Integer userId )
  {
    var user = this.userService.find( userId );
    assertNotNull( user );
    LOG.info( "Response: {}", user );
  }

  /**
   * Method to validate the search by id inexistent
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testFind_NotExists()
  {
    var user = this.userService.find( 999999 );
    assertNull( user );
  }

  /**
   * Test method for
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testCreate()
  {
    var dto = new UserDto();
    // Crear de acuerdo a la entidad

    var response = this.userService.create( dto );
    assertNotNull( response );
    assertEquals( 0, response.getHeader().getCode() );
    assertNotNull( response.getBody() );

    this.userService.delete( dto.getId() );
  }

  // •Se requiere crear un usuario en la aplicación, puede tener uno o más roles.
  // •El usuario debe ser único (username) y debe guardar el correo electrónico,
  // el nombre y apellido de la persona.
  // •En caso de ya existir el usuario o el correo electrónico deberá lanzar un
  // mensaje de error.
  // •En caso de seleccionar un rol que no exista el sistema deberá lanzar un
  // mensaje de error.
  // •En caso de no mandar roles deberá lanzar un mensaje de error.

  private RoleDto createRole(int id) {
    var role = new RoleDto();
    role.setId(id);
    return role;
  }

  /**
   * Test method to validate the email is unique
   *
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  void testEmailUnique() {
    var roleList = new ArrayList<RoleDto>();
    roleList.add(createRole(1));

    var dto = new UserDto();
    dto.setUsername("denise");
    dto.setEmail("denise.alford@company.net");
    dto.setName("Denise");
    dto.setLastName("Alford");
    dto.setRoles(roleList);

    var response = this.userService.create(dto);
    assertNotNull(response);
    assertEquals(ErrorCode.USER_ALREADY_EXISTS.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Test method to validate the email is unique whit many roles
   *
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  void testEmailUniqueManyRoles() {
    var roleList = new ArrayList<RoleDto>();
    roleList.add(createRole(1));
    roleList.add(createRole(2));
    roleList.add(createRole(3));

    var dto = new UserDto();
    dto.setUsername("denise");
    dto.setEmail("denise.alford@company.net");
    dto.setName("Denise");
    dto.setLastName("Alford");
    dto.setRoles(roleList);

    var response = this.userService.create(dto);
    assertNotNull(response);
    assertEquals(ErrorCode.USER_ALREADY_EXISTS.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Test method to validate the username is unique
   *
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  void testUsernameUnique() {
    var roleList = new ArrayList<RoleDto>();
    roleList.add(createRole(1));

    var dto = new UserDto();
    dto.setUsername("denise.alford");
    dto.setEmail("denis@company.net");
    dto.setName("Denise");
    dto.setLastName("Alford");
    dto.setRoles(roleList);

    var response = this.userService.create(dto);
    assertNotNull(response);
    assertEquals(ErrorCode.USER_ALREADY_EXISTS.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Test method to validate the username is unique with many roles
   *
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  void testUsernameUniqueManyRoles() {
    var roleList = new ArrayList<RoleDto>();
    roleList.add(createRole(1));
    roleList.add(createRole(2));
    roleList.add(createRole(3));

    var dto = new UserDto();
    dto.setUsername("denise.alford");
    dto.setEmail("denis@company.net");
    dto.setName("Denise");
    dto.setLastName("Alford");
    dto.setRoles(roleList);

    var response = this.userService.create(dto);
    assertNotNull(response);
    assertEquals(ErrorCode.USER_ALREADY_EXISTS.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Test method to validate a valid many roles
   *
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  void testValidManyRoles() {
    var roleList = new ArrayList<RoleDto>();
    roleList.add(createRole(1));
    roleList.add(createRole(10));
    roleList.add(createRole(2));
    roleList.add(createRole(20));
    roleList.add(createRole(3));
    roleList.add(createRole(30));

    var dto = new UserDto();
    dto.setUsername("axity");
    dto.setEmail("axity@axity.com");
    dto.setName("Axi");
    dto.setLastName("ty");
    dto.setRoles(roleList);

    var response = this.userService.create(dto);
    assertNotNull(response);
    assertEquals(ErrorCode.ROLE_NOT_FOUND.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Test method to validate that a rol is sent
   *
   * {@link com.axity.office.service.impl.UserServiceImpl#create(com.axity.office.commons.dto.UserDto)}.
   */
  @Test
  void testRoleNotEmpty() {
    var roleList = new ArrayList<RoleDto>();

    var dto = new UserDto();
    dto.setUsername("axity");
    dto.setEmail("axity@axity.com");
    dto.setName("Axi");
    dto.setLastName("ty");
    dto.setRoles(roleList);

    var response = this.userService.create(dto);
    assertNotNull(response);
    assertEquals(ErrorCode.ROLE_NOT_FOUND.getCode(), response.getHeader().getCode());
    assertNull(response.getBody());
  }

  /**
   * Method to validate update
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testUpdate()
  {
    var user = this.userService.find( 1 ).getBody();
    // TODO: actualizar de acuerdo a la entidad

    var response = this.userService.update( user );

    assertNotNull( response );
    assertEquals( 0, response.getHeader().getCode() );
    assertTrue( response.getBody() );
    user = this.userService.find( 1 ).getBody();

    // Verificar que se actualice el valor
  }

  /**
   * Method to validate an inexistent registry
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testUpdate_NotFound()
  {
    var user = new UserDto();
    user.setId(999999);
    var ex = assertThrows( BusinessException.class, () -> this.userService.update( user ) );

    assertEquals( ErrorCode.OFFICE_NOT_FOUND.getCode(), ex.getCode() );
  }

  /**
   * Test method for {@link com.axity.office.service.impl.UserServiceImpl#delete(java.lang.String)}.
   */
  @Test
  @Disabled("TODO: Actualizar la prueba de acuerdo a la entidad")
  void testDeleteNotFound()
  {
    var ex = assertThrows( BusinessException.class, () -> this.userService.delete( 999999 ) );
    assertEquals( ErrorCode.OFFICE_NOT_FOUND.getCode(), ex.getCode() );
  }
}
