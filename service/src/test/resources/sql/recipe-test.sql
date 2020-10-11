insert into RECIPE_CATEGORY(recipe_category_no, title, category_type, category_image_url) values
(1, '돼지고기', 1, null),
(2, '쌀', 1, null),
(11, '면', 2, null),
(12, '국물', 2, null);

insert into RECIPE(recipe_no, title, introduction, creator_no, creator_name, main_ingredient_category_no, kind_category_no, open_range, delete_yn) values
(1, '제목1', null, 1, '작성자1', 1, 11, 1, 'N'),
(2, '제목2', null, 1, '작성자1', 1, 12, 1, 'N'),
(3, '제목3', null, 1, '작성자1', 1, 11, 1, 'N'),
(4, '제목4', null, 1, '작성자1', 1, 12, 1, 'N'),
(5, '제목5', null, 1, '작성자1', 1, 12, 1, 'N'),
(6, '제목6', null, 1, '작성자1', 1, 12, 1, 'N'),
(7, '제목7', null, 1, '작성자1', 1, 11, 1, 'N'),
(8, '제목8', null, 1, '작성자1', 1, 12, 1, 'N'),
(9, '제목9', null, 1, '작성자1', 1, 11, 1, 'N'),
(10, '제목10', null, 2, '작성자2', 2, 12, 1, 'N'),
(11, '제목11', null, 2, '작성자2', 2, 12, 1, 'N'),
(12, '제목12', null, 1, '작성자1', 1, 12, 1, 'N'),
(13, '제목13', null, 1, '작성자1', 1, 11, 1, 'N'),
(14, '제목14', null, 2, '작성자2', 1, 12, 1, 'N'),
(15, '제목15', null, 1, '작성자1', 2, 11, 1, 'N'),
(16, '제목16', null, 2, '작성자2', 2, 12, 2, 'N'),
(17, '제목17', null, 2, '작성자2', 2, 12, 2, 'N'),
(18, '제목18', null, 1, '작성자1', 1, 12, 3, 'N');
