import request from '../api/request';

export const login = (data) => {
  return request.post('/login', data);
};