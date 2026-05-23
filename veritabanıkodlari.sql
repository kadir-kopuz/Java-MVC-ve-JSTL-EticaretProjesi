ALTER TABLE categories
	ADD COLUMN parent_category_id INT NULL AFTER description;

ALTER TABLE categories
	ADD CONSTRAINT fk_categories_parent
	FOREIGN KEY (parent_category_id) REFERENCES categories(id)
	ON DELETE SET NULL;
