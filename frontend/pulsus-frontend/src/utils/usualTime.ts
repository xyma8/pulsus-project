import { parseISO, format, formatDistanceToNow, isToday, isYesterday } from 'date-fns';
import { ru } from 'date-fns/locale';

export function dateToFormatted(date: string):string {
    const parseData = parseISO(date);
    var formattedDate = format(parseData, 'dd MMMM yyyy, HH:mm', { locale: ru });
    //var relativeDate = formatDistanceToNow(date, { addSuffix: true, locale: ru });

    const isDateToday = isToday(parseData);
    const isDateYesterday = isYesterday(parseData);
    
    if (isDateToday) {
        formattedDate = `Сегодня, ${format(parseData, 'HH:mm', { locale: ru })}`;
    } else if (isDateYesterday) {
        formattedDate = `Вчера, ${format(parseData, 'HH:mm', { locale: ru })}`;
    }

    return formattedDate;
}