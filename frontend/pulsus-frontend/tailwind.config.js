/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    container: {
      center: true,
      padding: '8rem',
    },
    extend: {
      colors:{
        primary: '#007BFF', // Этот цвет будет использоваться для основных элементов интерфейса, таких как кнопки, ссылки и акценты.
        secondary: '#28A745', // Вторичный цвет можно использовать для подтверждающих элементов, таких как успешные сообщения и иконки.
        accent: '#FFC107', // Этот цвет может быть использован для выделения важных элементов и привлечения внимания к определенным действиям или информациям.
        main_background: '#F9F8F5', // Используется для основного фона страниц
        block_background: '#FFFFFF', // Используется для основного фона блоков
        text: '#343A40', // Для основного текста на сайте.
        main_text_button: '#FFFFFF', // Основной цвет текста кнопок
        primary_hover_button: '#0056b3', // Цвет для наведения на кнопки с основным цветом
        secondary_hover_button: '#218838', // Цвет для наведения на кнопки с вторичным цветом
        error: '#DC3545', // Красный цвет для ошибок
        danger: '#FF0000',
        danger_hover: '#B00000'
      }
    },
  },
  plugins: [],
}

