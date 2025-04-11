package com.myapp.restapi.util.beanvalidation;

import jakarta.validation.groups.Default;

public interface ValidationGroups {
	interface Create{};
	interface CreatePlusDefault extends Create, Default{};
	interface Update{};
	interface UpdatePlusDefault extends Update,Default{};

}
