function successResponseHandler<T>(data: T): { status: number; data: T } {
  return {
    status: 200,
    data,
  };
}
