import request from '../api/request';

export const getSummary = (yearMonth, page = 1, rows = 10) => {
    return request.get('/admin/getSummary', { params: {yearMonth, page, rows } });
};