import request from '../api/request';

export const checkIn = () => {
    return request.post('/checkIn');
};