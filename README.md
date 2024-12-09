# FoodMenuApp

#### Data Handling
1. Utilizes Ktor to handle API , supporting pagination and category-based filtering.
2. Implements Room database for caching fetched data.
3. Adheres to MVVM/MVI design patterns with a repository layer for clean and maintainable code.
4. Use Koin for seamless dependency injection.
5. Coroutines for efficient background threading operations.
6. Organized with clear package structures, consistent naming conventions, and extensive comments for easy navigation and scalability.

#### UI Design
1. Material Design 3: Adheres to Material Design guidelines with a clean and simple color palette.
2. The "View Order" button remains hidden until the user starts selecting products, enhancing the UI responsiveness
3. Implements a debounce mechanism for the search view
4. Utilizes the NavController in combination with a navigation bar for smooth navigation between screens.
5. Theme: Currently Support white theme only.
6. Localization: Currently Support the English language only.

## small app demo :
https://github.com/user-attachments/assets/160a2ec4-974e-4ff7-b45a-4cd72dd5932e

<br>

## screenshot for  two different screen ration 
<img width="1054" alt="Screenshot 2024-12-09 at 3 53 59 AM" src="https://github.com/user-attachments/assets/aea18773-db97-4a19-b90a-4f173d5e4d4d">
<img width="1068" alt="Screenshot 2024-12-09 at 3 53 04 AM" src="https://github.com/user-attachments/assets/8f392bfd-b240-4d9f-86ed-e2ed6bad7cfd">

<br> <br>

<h2>MOCK-API & SCHEME</h2>

#### Category part:
<img width="716" alt="Screenshot 2024-12-09 at 4 34 26 AM" src="https://github.com/user-attachments/assets/7eecc50f-f0b2-4879-8753-d8fc6c06812d">
<img width="1424" alt="Screenshot 2024-12-09 at 4 47 58 AM" src="https://github.com/user-attachments/assets/3482a6ec-a73d-42fc-b44d-d1894e6e6550">

##### Json File
```
[
  {
    "id": 1,
    "name": "Breakfast"
  },
  {
    "id": 2,
    "name": "Lunch"
  },
  {
    "id": 3,
    "name": "Dinner"
  },
  {
    "id": 4,
    "name": "Sweets"
  },
  {
    "id": 5,
    "name": "Ice"
  },
  {
    "id": 6,
    "name": "Label1"
  },
  {
    "id": 7,
    "name": "Label2"
  }
]
```
***

#### Products part:
##### product handler script to support two things 
1. fetch products list based on category_id
2. support pagination
   
```
schema "product"
data = generate 300
filtered_data = data.select { |item| item["category"]["id"] == params["category_id"].to_i }

# Apply pagination logic
offset = params["offset"].to_i
limit = params["limit"].to_i
paginated_data = filtered_data[offset, limit]

{
  result: paginated_data || []
}
```

<img width="712" alt="product_api" src="https://github.com/user-attachments/assets/e179d75d-cf75-4026-879f-76f6da7dabec">
<img width="1415" alt="product_scheme" src="https://github.com/user-attachments/assets/51a136b5-c77d-4dd2-a4df-64637b822dad">

##### Json File
```
[
  {
    "id": "42-401-3862",
    "name": "Spaghetti Carbonara",
    "description": "Delicious spaghetti with meatballs",
    "image": "https://cdn.pixabay.com/photo/2024/02/16/15/36/recipe-8577854_1280.jpg",
    "category": {
      "id": 2,
      "name": "Lunch"
    },
    "price": 3.4
  },
  {
    "id": "73-719-3222",
    "name": "Chicken Alfredo",
    "description": "Creamy chicken alfredo pasta",
    "image": "https://cdn.pixabay.com/photo/2023/06/12/11/34/mushrooms-8058299_1280.jpg",
    "category": {
      "id": 4,
      "name": "Sweets"
    },
    "price": 4.4
  },
  {
    "id": "49-664-7020",
    "name": "Beef Stroganoff",
    "description": "Savory beef stew with vegetables",
    "image": "https://cdn.pixabay.com/photo/2024/04/01/05/18/green-8667981_1280.jpg",
    "category": {
      "id": 1,
      "name": "Breakfast"
    },
    "price": 5.4
  },
  {
    "id": "83-091-7582",
    "name": "Vegetable Stir Fry",
    "description": "Crispy fried chicken with mashed potatoes",
    "image": "https://media.istockphoto.com/id/157507721/photo/hamburger-patty-on-grill-with-fire.jpg",
    "category": {
      "id": 2,
      "name": "Lunch"
    },
    "price": 6.4
  },
  {
    "id": "91-772-3413",
    "name": "Shrimp Scampi",
    "description": "Fresh garden salad with balsamic vinaigrette",
    "image": "https://cdn.pixabay.com/photo/2023/01/09/10/56/meal-7707134_1280.jpg",
    "category": {
      "id": 4,
      "name": "Sweets"
    },
    "price": 7.4
  },
  {
    "id": "80-034-3804",
    "name": "Tofu Pad Thai",
    "description": "Delicious spaghetti with meatballs",
    "image": "https://media.istockphoto.com/id/912629972/photo/chicken-kebab-with-bell-pepper.jpg",
    "category": {
      "id": 4,
      "name": "Sweets"
    },
    "price": 8.4
  },
  {
    "id": "93-681-2547",
    "name": "Spaghetti Carbonara",
    "description": "Creamy chicken alfredo pasta",
    "image": "https://cdn.pixabay.com/photo/2024/05/10/10/51/ai-generated-8752890_1280.jpg",
    "category": {
      "id": 3,
      "name": "Dinner"
    },
    "price": 9.4
  },
  {
    "id": "12-838-8903",
    "name": "Chicken Alfredo",
    "description": "Savory beef stew with vegetables",
    "image": "",
    "category": {
      "id": 3,
      "name": "Dinner"
    },
    "price": 10.4
  },
  {
    "id": "25-142-1478",
    "name": "Beef Stroganoff",
    "description": "Crispy fried chicken with mashed potatoes",
    "image": "https://cdn.pixabay.com/photo/2024/02/16/15/36/recipe-8577854_1280.jpg",
    "category": {
      "id": 4,
      "name": "Sweets"
    },
    "price": 11.4
  },
  {
    "id": "51-450-9379",
    "name": "Vegetable Stir Fry",
    "description": "Fresh garden salad with balsamic vinaigrette",
    "image": "https://cdn.pixabay.com/photo/2023/06/12/11/34/mushrooms-8058299_1280.jpg",
    "category": {
      "id": 1,
      "name": "Breakfast"
    },
    "price": 12.4
  }
]
```

