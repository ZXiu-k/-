import request from '../api/request';

export const getEmpInfo = () => {
    return request.get('/getInfo');
};