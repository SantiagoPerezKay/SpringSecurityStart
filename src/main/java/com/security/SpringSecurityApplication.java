package com.security;

import com.security.persistence.entity.PermissionEntity;
import com.security.persistence.entity.RoleEntity;
import com.security.persistence.entity.RoleEnum;
import com.security.persistence.entity.UserEntity;
import com.security.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}


	@Bean
	CommandLineRunner init(UserRepository userRepository){ //metodo para ejecutatar cuando arranca la aplicacion, en este caso para cargar la BD.

		return args -> {
			//creacion de  permisos:

			//create
			PermissionEntity createPermission=PermissionEntity.builder()
					.name("CREATE")
					.build();
			//read
			PermissionEntity readPermission=PermissionEntity.builder()
					.name("READ")
					.build();
			//update
			PermissionEntity updatePermission=PermissionEntity.builder()
					.name("UPDATE")
					.build();
			//delete
			PermissionEntity deletePermission=PermissionEntity.builder()
					.name("DELETE")
					.build();
			//global
			PermissionEntity globalPermission=PermissionEntity.builder()
					.name("GLOBAL")
					.build();

			//creacion de roles:

			//admin
			RoleEntity roleAdmin= RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();
			//user
			RoleEntity roleUser= RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission,readPermission))
					.build();
			//invited
			RoleEntity roleInvited= RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();
			//developer
			RoleEntity roleDeveloper= RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission,readPermission,updatePermission,deletePermission,globalPermission))
					.build();

			//creacion de  usuarios:

			UserEntity santiago=new UserEntity().builder()
					.username("santiago")
					.password("123")
					.isenabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.accountNoLocked(true)
					.roles(Set.of(roleAdmin ))
					.build();

			UserEntity nicolas=new UserEntity().builder()
					.username("nicolas")
					.password("123")
					.isenabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.accountNoLocked(true)
					.roles(Set.of(roleUser ))
					.build();

			UserEntity marcelo=new UserEntity().builder()
					.username("marcelo")
					.password("123")
					.isenabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.accountNoLocked(true)
					.roles(Set.of(roleInvited ))
					.build();

			UserEntity roberto=new UserEntity().builder()
					.username("roberto")
					.password("123")
					.isenabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.accountNoLocked(true)
					.roles(Set.of(roleDeveloper ))
					.build();

			userRepository.saveAll(List.of(santiago,marcelo,roberto,nicolas));
		};


	}

}
