json.array!(@bitmaps) do |bitmap|
  json.extract! bitmap, :id, :filename, :content
  json.url bitmap_url(bitmap, format: :json)
end
