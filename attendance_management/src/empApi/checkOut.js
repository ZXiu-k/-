import request from '../api/request';

export const checkOut = () => {
    return request.post('/checkOut');
};